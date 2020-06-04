import java.util.Random;
import sheffield.EasyWriter;

public class EpuzzGen {
    EasyWriter screen = new EasyWriter();
    int[][] tar = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    Random gen;

    public EpuzzGen() {
        this.gen = new Random();
    }

    public EpuzzGen(int seed) {
        this.gen = new Random((long)seed);
    }

    public int[][] puzzGen(int diff) {
        int[][] puzz = new int[3][3];

        int i;
        int j;
        for(int n = 0; n <= 8; puzz[i][j] = n++) {
            i = this.gen.nextInt(3);

            for(j = this.gen.nextInt(3); puzz[i][j] != 0; j = this.gen.nextInt(3)) {
                i = this.gen.nextInt(3);
            }
        }

        if(this.impossible(puzz, diff)) {
            puzz = this.puzzGen(diff);
        }

        return puzz;
    }

    private boolean impossible(int[][] puzzq, int diff) {
        int[] flatp = new int[8];
        int f = 0;

        int invcount;
        int mhat;
        for(invcount = 0; invcount <= 2; ++invcount) {
            for(mhat = 0; mhat <= 2; ++mhat) {
                if(puzzq[invcount][mhat] > 0) {
                    flatp[f] = puzzq[invcount][mhat];
                    ++f;
                }
            }
        }

        invcount = 0;

        for(mhat = 0; mhat <= 6; ++mhat) {
            for(int l = mhat + 1; l <= 7; ++l) {
                if(flatp[l] < flatp[mhat]) {
                    ++invcount;
                }
            }
        }

        mhat = this.manhattan(puzzq, this.tar);
        return invcount % 2 != 0 || mhat > diff;
    }

    private int manhattan(int[][] s, int[][] t) {
        int d = 0;
        int si = 0;
        int sj = 0;

        for(int n = 0; n <= 8; ++n) {
            int i;
            int j;
            for(i = 0; i <= 2; ++i) {
                for(j = 0; j <= 2; ++j) {
                    if(s[i][j] == n) {
                        si = i;
                        sj = j;
                    }
                }
            }

            for(i = 0; i <= 2; ++i) {
                for(j = 0; j <= 2; ++j) {
                    if(t[i][j] == n) {
                        d = d + Math.abs(i - si) + Math.abs(j - sj);
                    }
                }
            }
        }

        return d;
    }
}
