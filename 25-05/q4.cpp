#include <bits/stdc++.h>
using namespace std;

string minWindow(string ss, string tt) {
    int n = ss.length();
    if (tt.length() > n) {
        return "";
    }

    map<char, int> mp;
    for (char ch : tt) {
        mp[ch]++;
    }

    int reqCount = tt.length();
    int i = 0, j = 0;
    int windowSize = INT_MAX;
    int st = 0;

    while (j < n) {
        char ch = ss[j];
        if (mp[ch] > 0) {
            reqCount--;
        }
        mp[ch]--;

        while (reqCount == 0 && i <= j) {
            if (j - i + 1 < windowSize) {
                windowSize = j - i + 1;
                st = i;
            }

            mp[ss[i]]++;
            if (mp[ss[i]] > 0) {
                reqCount++;
            }
            i++;
        }

        j++;
    }

    return windowSize == INT_MAX ? "" : ss.substr(st, windowSize);
}

int main() {
    string s = "ADOBECODEBANC";
    string t = "ABC";

    string result = minWindow(s, t);
    cout << result << endl;

    return 0;
}