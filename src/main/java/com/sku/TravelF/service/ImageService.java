package com.sku.TravelF.service;

import com.sku.TravelF.domain.Image;

import java.util.ArrayList;

public interface ImageService {
    public void save(Image image);
    public ArrayList<Image> findImageList(Long id);
}
