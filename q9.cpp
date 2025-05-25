

#include <bits/stdc++.h>
using namespace std;
int ans(int n){
    bool neg= 0;
    if(n<0){
        neg=1;
    }
    
    string st = to_string(abs(n));
    sort(st.begin(),st.end());
    if(neg){
        sort(st.begin(),st.end(),greater<int>());
        return (-(stoi(st)));
    }
    
    return stoi(st);
    
}

int main() {
   int n = 50221;
   int an= ans(n);
   cout <<an;
}
