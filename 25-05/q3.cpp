#include <bits/stdc++.h>
using namespace std;

bool brackets(string s) {
    int n = s.size();
    stack<char> st;

    for (int i = 0; i < n; i++) {
        if (s[i] == '(' || s[i] == '{' || s[i] == '[') {
            st.push(s[i]);
        } else {
            if (st.empty()) return false;

            char top = st.top();
            if ((s[i] == ')' && top == '(') ||
                (s[i] == '}' && top == '{') ||
                (s[i] == ']' && top == '[')) {
                st.pop();
            } else {
                return false;
            }
        }
    }

    return st.empty();
}

int main() {
    string test = "{[()]}";
    if (brackets(test)) {
        cout << "true" << endl;
    } else {
        cout << "false" << endl;
    }

    return 0;
}