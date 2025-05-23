//Two Sum II - Input array is sorted
//Given a sorted array of integers, return the indices of the two numbers such that they add up to a specific target.

#include <bits/stdc++.h>
using namespace std;

vector<int> twoSum(int low , int high , vector<int> arr1 , int target){
    while(low<=high){
        if((arr1[low] + arr1[high]) > target){
            high--;
        }
        else if((arr1[low] + arr1[high]) < target){
            low++;
        }
        else{
            return {low+1 , high+1};
        }
    }
    return{};
}

int main()
{
    int n;
    cin >> n;
    vector<int> arr1(n);
    for(int i = 0;i<n;i++){
        cin >> arr1[i];
    }
    
    int target;
    cin >> target;
    
    vector<int> ans = twoSum(0,n-1,arr1 , target);
    
    for(int i = 0;i<ans.size();i++){
        cout<< ans[i];
        cout << " ";
    }

    return 0;
}
