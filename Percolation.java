/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private int []sites;
    private int numberOfOpenSites;
    private int[] siteStatus;
    private WeightedQuickUnionUF unionFind;

    public Percolation(int n){
        this.n = n;
        int numberOfSites = n*n;
        unionFind = new WeightedQuickUnionUF(numberOfSites+2);
        sites = new int[numberOfSites + 2];
        siteStatus = new int[numberOfSites + 2];
        for(int i = 1; i <= numberOfSites; i++){
            sites[i] = i;
            siteStatus[i] = 0;
        }
    }

    private int getIndex(int row, int col){
        if(row <= 0 || row > n) throw new IllegalArgumentException();
        if(col <= 0 || col > n) throw new IllegalArgumentException();

        return ((row -1) * n + (col-1)) + 1 ;
    }

    public void open(int row, int col){
        numberOfOpenSites++;
       int currentSite = getIndex(row, col);
       siteStatus[currentSite] = 1;

        connectToTopVirtualSite(row, currentSite);

        connectToBottomVirtualSite(row, currentSite);

        connectToTopSite(row, col, currentSite);

        connectToBottomSite(row, col, currentSite);

        connectToLeftSite(row, col, currentSite);

        connectToRightSite(row, col, currentSite);
    }

    private void connectToTopVirtualSite(int row, int currentSite) {
        if(row == 1){
            unionFind.union(0, currentSite);
        }
    }

    private void connectToBottomVirtualSite(int row, int currentSite) {
        if(row == n){
            unionFind.union(n*n+1, currentSite);
        }
    }

    private void connectToTopSite(int row, int col, int currentSite) {
        if(row-1 > 0){
            int bottomSite = getIndex(row-1, col);
            if(siteStatus[bottomSite] == 1){
                unionFind.union(currentSite, bottomSite);
            }
        }
    }

    private void connectToRightSite(int row, int col, int currentSite) {
        if(col + 1 <= n){
            int rightSite = getIndex(row, col+1);
            if(siteStatus[rightSite] == 1){
                unionFind.union(rightSite, currentSite);
            }
        }
    }

    private void connectToLeftSite(int row, int col, int currentSite) {
        if(col - 1 > 0){
            int leftSite = getIndex(row, col-1);
            if(siteStatus[leftSite] == 1){
                unionFind.union(leftSite, currentSite);
            }
        }
    }

    private void connectToBottomSite(int row, int col, int currentSite) {
        if(row + 1 <= n){
            int topSite = getIndex(row+1, col);
            if(siteStatus[topSite] == 1){
                unionFind.union(topSite, currentSite);
            }
        }
    }

    public boolean isOpen(int row, int col){
        return siteStatus[getIndex(row, col)] == 1;
    }

    public boolean isFull(int row, int col){
        return unionFind.connected(0, getIndex(row, col));
    }

    public int numberOfOpenSites(){
        return numberOfOpenSites;
    }

    public boolean percolates(){
        return unionFind.connected(0, n*n+1);
    }

    public static void main(String[] args) {
    }
}
