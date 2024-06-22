package com.tp.projectbase.entity;

public class Action {
    private Integer down = 0;
    private Integer click = 0;
    private Double trending;

    public Action() {
    }

    public Integer getDown() {
        return down;
    }

    public void setDown(Integer down) {
        this.down = down;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Double getTrending() {
        if (trending == null) {
            // down * down / (click * 100)
            return Math.pow(down, 2) / (click * 100);
        }
        return trending;
    }

    public void setTrending(Double trending) {
        this.trending = trending;
    }

    @Override
    public String toString() {
        return "{" +
                "down=" + down +
                ", click=" + click +
                '}';
    }
}
