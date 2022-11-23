from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
import time
import MySQLdb
import os

#def crawling(areaList):

#step2.검색할 키워드 입력
#query = input('검색할 키워드를 입력하세요: ')

options = Options()

options.add_argument('--start-fullscreen')

#step3.크롬드라이버로 원하는 url로 접속
url = 'https://korean.visitkorea.or.kr/list/ms_list.do?choiceTag=&choiceTagId='
driver = webdriver.Chrome('D:/chromedriver', chrome_options=options)
driver.get(url)
time.sleep(1)

#step4.검색창에 키워드 입력 후 엔터
#search_box = driver.find_element(By.CSS_SELECTOR,"input#query")
#search_box.send_keys(query)
#search_box.send_keys(Keys.RETURN)
#time.sleep(2)

conn = MySQLdb.connect(
    user="diting981113@gmail.com",
    passwd="y2kxtom16spu!",
    host="ec2-3-39-1-201.ap-northeast-2.compute.amazonaws.com",
    db="TravelF"
    # charset="utf-8"
)
# print(type(conn))
# # <class 'MySQLdb.connections.Connection'>
cursor = conn.cursor()
# print(type(cursor))
# # <class 'MySQLdb.cursors.Cursor'>

##########CREATE TABLE############
#ursor.execute("DROP TABLE recommended")
#cursor.execute("CREATE TABLE recommended (title text, file_name text, hashtag text, area_txt text)")
#conn.commit()

#step5.뉴스 탭 클릭
driver.find_element(By.XPATH,'//*[@id="contents"]/div[2]/div[2]/div[2]/ul[2]/li[@id=39]').click() #areaList
time.sleep(1)
#driver.find_element(By.XPATH,'//*[@id="contents"]/div[2]/div[2]/div[2]/ul[3]/li[@id=9]').click() #sigunguList
time.sleep(1)
for page in range(1, 453):
    if page%5==1 and page!=1:
        if page==6:
            driver.find_element(By.XPATH,'//*[@id="contents"]/div[2]/div[1]/div[2]/a[6]').click()
            time.sleep(1)
        else:
            driver.find_element(By.XPATH,'//*[@id="contents"]/div[2]/div[1]/div[2]/a[8]').click()
            time.sleep(1)
    driver.find_element(By.XPATH,'//*[@id="contents"]/div[2]/div[1]/div[2]/a[@id='+str(page)+']').click()
    time.sleep(1)
    titles = driver.find_elements(By.CSS_SELECTOR,".tit")
    tags = driver.find_elements(By.CSS_SELECTOR,".tag")
    area_txts = driver.find_elements(By.CSS_SELECTOR,".area_txt  p:nth-child(2)")

    title = []
    tag = []
    area_txt = []
    for i in titles:
        title.append(i.text)

    for i in area_txts:
        area_txt.append(i.text)

    for i in tags:
        tag.append(i.text)
    #step8.뉴스 썸네일 이미지 추출

    news_thumbnail = driver.find_elements(By.CSS_SELECTOR,".photo img")

    recommended_list = []
    link_thumbnail = []

    for img in news_thumbnail:

        link_thumbnail.append(img.get_attribute('src'))


    # 이미지 저장할 폴더 생성

    # path_folder의 경로는 각자 저장할 폴더의 경로를 적어줄 것(ex.img_download)
    path_folder = 'C:/Users/YeomJuheon/OneDrive - skuniv.ac.kr/바탕 화면/TourList/src/main/webapp/crawling'

    if not os.path.isdir(path_folder):
        os.mkdir(path_folder)


    # 이미지 다운로드

    i = 0
    # for link in link_thumbnail:
    #     i += 1
    #     urlretrieve(link, path_folder + f'{i}.jpg')  #link에서 이미지 다운로드, './imgs/'에 파일명은 index와 확장자명으로

    for i in range(0, len(link_thumbnail)):
        recommended_list.append((title[i], link_thumbnail[i], tag[i+1], area_txt[i]))


    def input_crawl_data(data):
        global cursor,conn
        for title,link,tag,area_txt in data:
            cursor.execute(f"INSERT INTO recommended(title,file_name,hashtag,area_txt) VALUES(\"{title}\",\"{link}\",\"{tag}\",\"{area_txt}\")")


    input_crawl_data(recommended_list)
    conn.commit()
    # cursor.execute("SELECT * FROM recommended")
    # rows = cursor.fetchall()
    # for row in rows:
    #     print(row)