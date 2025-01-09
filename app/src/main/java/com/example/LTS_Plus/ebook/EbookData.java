package com.example.LTS_Plus.ebook;

public class EbookData {
    private final String pdfTitle;
    private final String pdfUrl;

    public EbookData(String pdfTitle, String pdfUrl) {
        this.pdfTitle = pdfTitle;
        this.pdfUrl = pdfUrl;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

}