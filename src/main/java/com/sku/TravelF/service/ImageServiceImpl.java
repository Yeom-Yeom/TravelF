package com.sku.TravelF.service;

import com.sku.TravelF.domain.Image;
import com.sku.TravelF.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;

    // 리스트 반환
    public ArrayList<Image> findImageList(Long id) {
        return imageRepository.findAllByJournal_IdOrderByModifiedDateDesc(id);
    }
    public void save(Image image) {
         imageRepository.save (image);
    }
}
