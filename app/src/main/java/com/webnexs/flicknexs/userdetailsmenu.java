package com.webnexs.flicknexs;

class userdetailsmenu {

    private String name1, name2,image;
    private int imagedp;


    public userdetailsmenu(String name1, String image, String name2, int imagedp ) {

        this.name1 = name1;
        this.image = image;
        this.name2 = name2;
        this.imagedp = imagedp;

    }

    public userdetailsmenu() {

    }


    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getImagedp() {
        return imagedp;
    }

    public void setImagedp(int imagedp) {
        this.imagedp = imagedp;
    }



}
