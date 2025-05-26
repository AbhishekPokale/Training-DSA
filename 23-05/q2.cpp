#include<bits/stdc++.h>
using namespace std;

int subarr(vector<int> & arr,  int k){
    int n = arr.size();
    unordered_map<int,int> mp;
    mp[0] = 1;
    int prefix , count = 0;
    for(int i = 0;i<n;i++){
        prefix += arr[i];
        int r = prefix - k;
        count += mp[r];
        mp[prefix]++;
    }
    
    return count;
}

int main()
{
    int n;cin >> n;
    int k;cin >> k;
    vector<int> arr(n);
    for(int i = 0;i<n;i++){
        cin >> arr[i];
    }
    
    cout << subarr(arr,k);

    return 0;
}
