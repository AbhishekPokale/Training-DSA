#include <bits/stdc++.h>
using namespace std;

int main() {
    string w1 = "listen", w2 = "silent";
    cout << "Input: " << w1 << " " << w2 << endl;

    if (w1.length() != w2.length()) {
        cout << "Output: false" << endl;
        return 0;
    }

    unordered_map<char, int> mp1, mp2;

    for (char ch : w1) mp1[ch]++;
    for (char ch : w2) mp2[ch]++;

    if (mp1 == mp2)
        cout << "Output: true" << endl;
    else
        cout << "Output: false" << endl;

    return 0;
}