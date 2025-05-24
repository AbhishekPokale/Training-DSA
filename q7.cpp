// Longest Substring Without Repeating Characters
// Given a string, find the length of the longest substring without repeating characters.


#include <bits/stdc++.h>
using namespace std;

int findString(string s) {
    int n = s.length();
    int maxLength = 0;
    unordered_set<char> st;
    int l = 0;
    
    for(int i = 0; i < n; i++) {
        if(st.count(s[i]) == 0) {
            st.insert(s[i]);
            maxLength = max(i - l + 1, maxLength);
        } else {
            while(st.count(s[i])) {
                st.erase(s[l]);
                l++;
            }
            st.insert(s[i]);
        }
    }
    
    return maxLength;
}

int main() {
    string test = "abcabcbb";
    cout << findString(test) << endl;
    return 0;
}
