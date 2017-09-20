#include <iostream>
#include <vector>
using namespace std;

vector<int> readVals(){
  int val;
  vector<int> nums;
  cout << "Enter numbers:";
  while(cin >> val){
    nums.push_back(val);
  }
  return nums;
}


void printVals(vector<int> nums){
  for(int i = 0; i < nums.size();i++){
    cout << nums[i]<< " ";
  }
  cout << endl;
}

vector<int> filter(vector<int> nums){
  vector<int> filt;
  for(int i = 0; i < nums.size(); i++){
    if(nums[i] > 0){
      filt.push_back(nums[i]);
    }
  }
  return filt;
}
int main(){
  vector<int> nums = readVals();
  printVals(nums);
  cout << "Filter version: < 0:   ";
  vector<int> filt = filter(nums);
  printVals(filt);
  cout << "Original vector";
  printVals(nums);
  return 0;
}
