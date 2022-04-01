package edu.brown.cs32.spring22.livecode.mar17;

public class Star {
    private int i;
    private String name;
    private double[] coords;

    public Star(int i, String s, double rx, double ry, double rz) {
        this.i = i;
        this.name = s;
        this.coords = new double[]{rx, ry, rz};
    }

    public double[] getCoords() {
        return this.coords;
    }

    public double distance(Star target) {
        double[] targetCoords = target.getCoords();
        return Math.sqrt(
                Math.pow(this.coords[0] - targetCoords[0], 2)
                        + Math.pow(this.coords[1] - targetCoords[1], 2)
                        + Math.pow(this.coords[2] - targetCoords[2], 2)
        );
    }
}
