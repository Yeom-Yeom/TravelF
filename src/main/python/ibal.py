# This Python file uses the following encoding: utf-8

import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer

test = pd.read_csv(
    r'D:/TourList/src/main/webapp/crawling/recommended.csv',
    encoding='utf-8', low_memory=False)
test = test[['id', 'title', 'file_name', 'hashtag', 'area_txt']]
test = test.dropna()
test['hashtag'] = test['hashtag'].fillna('')

tfidf = TfidfVectorizer(stop_words='english')

tfidf_matrix = tfidf.fit_transform(test['hashtag'])

cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

cosine_sim = pd.DataFrame(cosine_sim, index=test.index, columns=test.index)

indices = pd.Series(test.index, index=test['title'])

def content_recommender(title, n_of_recomm):

    # title에서 여행지 index 받아오기
    idx = indices[title]

    # 주어진 여행지와 다른 여행지의 similarity를 가져온다
    sim_scores = cosine_sim[idx]

    # similarity 기준으로 정렬하고 n_of_recomm만큼 가져오기
    sim_scores = sim_scores.sort_values(ascending=False)[1:n_of_recomm + 1]

    # id 반환
    return test.loc[sim_scores.index]['title']

print(content_recommender("비올레타",10))

