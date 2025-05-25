
#include <iostream>
#include <vector>
#include <unordered_map>
#include <string>
using namespace std;

string getWord(string& s) {
    int arr[26] = {0};
    
    for(char& ch : s) {
        arr[ch - 'a']++;
    }
    
    string word = "";
    
    for(int i = 0; i < 26; i++) {
        int freq = arr[i];
        if(freq > 0) {
            word += string(freq, i + 'a');
        }
    }
    
    return word;
}

vector<vector<string>> groupAnargam(vector<string>& strs) {
    int n = strs.size();
    unordered_map<string, vector<string>> mp;
    vector<vector<string>> result;

    for(int i = 0; i < n; i++) {
        string word = getWord(strs[i]);
        string curr = strs[i];
        mp[word].push_back(curr);
    }

    for(auto& it : mp) {
        result.push_back(it.second);
    }

    return result;
}

int main() {
    vector<string> input = {"eat", "tea", "tan", "ate", "nat", "bat"};
    
    vector<vector<string>> grouped = groupAnargam(input);
    
    for(auto& group : grouped) {
        for(auto& word : group) {
            cout << word << " ";
        }
        cout << endl;
    }

    return 0;
}
