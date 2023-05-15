package com.heodae.webpconverter.service;

import java.io.IOException;

public interface WebpConvertService {
    String convertToWebP(String filePath) throws IOException;
}
