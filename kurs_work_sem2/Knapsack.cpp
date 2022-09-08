#include "Knapsack.h"

using namespace std;

void traceResult(int **dp, Fuel *f, int n, int s, LinkedList<Fuel> *list, int &del, fstream &fs) {
    if (dp[n][s] == 0) {
        return;
    }
    if (dp[n - 1][s] == dp[n][s]) {
        traceResult(dp, f, n - 1, s, list, del, fs);
    } else {
        traceResult(dp, f, n - 1, s - f[n - 1].mass, list, del, fs);
        fs << "м " << f[n - 1].mass << " л " << f[n - 1].n  <<  ", ";
        list->remove(n - 1 - del);
        del++;
    }
}

void calc(Fuel *f, int n, int s, LinkedList<Fuel> *list, fstream &fs) {

    int **dp = new int *[n + 1];
    for (int i = 0; i < n + 1; i++) {
        dp[i] = new int[s + 1];
    }

    for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= s; j++) {
            if (i == 0 || j == 0) {
                dp[i][j] = 0;
            } else {
                if (j >= f[i - 1].mass) {
                    dp[i][j] = max(dp[i - 1][j], dp[i - 1][j - f[i - 1].mass] + f[i - 1].cost);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
    }
    int del = 0;
    traceResult(dp, f, n, s, list, del, fs);
    for (int i = 0; i < n + 1; i++) {
        delete[] dp[i];
    }
    delete[] dp;
    //return dp[n][s];
}