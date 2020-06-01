#include<iostream>
#include <list> 
#include <iterator> 
#include <bits/stdc++.h>

using namespace std;
int i=1;                                        //global depth of table

//buckets for storing data
class Bucket
{
    public:
        std::vector<int> values;
        int depth,size;

        Bucket(int depth){
            this->depth = depth;
            size=3;
        }
        int isFull()
        {
            if(values.size()==size)
                return 1;
            else
                return 0;
        }
        
        int insert(int x)                               //set values into buckets
        {   if(isFull())
                return 0;
            else{
                values.push_back(x);
                return 1;
            }
        }
   
        void print_value() { 
            
            for(auto it = values.begin(); it != values.end(); ++it) 
                cout << '\t' << *it; 
            cout << '\n'; 
        }
        int increase_Depth()
        {
            depth++;
            return depth;
        }
        std::vector<int> copy()
        {
            std::vector<int> temp(values.begin(),values.end());
            return temp;
        }
        void clear()
        {
            values.clear();
        }
        int find(int x) { 
            
            for(auto it = values.begin(); it != values.end(); ++it) 
                if(*it == x)
                    return 1; 
            return 0; 
        }
        void search(int x)
        {
            
            int found = find(x);
            if(found)
            {
                 cout<<"found = "<<x<<endl;
            }
            else
            {
                cout<<"value not exist"<<endl;
            }
        } 
};

//main index table to maintain buckets
class table
{
    public:
        std::vector<Bucket*> buckets;
        int global_depth;

        table(int intial_depth){
            global_depth = intial_depth;
            
            for(int i = 0 ; i < 1<<intial_depth ; i++ )
            {
                 buckets.push_back(new Bucket(intial_depth));
            }
             
        }

        
        int hash(int n){
            int hash = n&((1<<global_depth)-1);
            return hash;
        }

        void insert(int value){

            int bucket_no = hash(value);
            int status = buckets[bucket_no]->insert(value);
            if(status==1)
                cout<<"Inserted key "<<value<<" in bucket "<<bucket_no<<endl;
            else if(status==0)
            {
                split(bucket_no);
                insert(value);
            }
            
        }

        void print(){
            cout<<"buckets:"<<endl;
            for (int i = 0; i < buckets.size(); ++i)
            {   cout<<i<<" =>";
                buckets[i]->print_value();
            }
        }

        void grow(void)
        {
            for(int i = 0 ; i < 1<<global_depth ; i++ )
                buckets.push_back(buckets[i]);
            global_depth++;
        }
        int split_hash(int bucket_no, int depth)
        {
            return bucket_no^(1<<(depth-1));
        }
        void split(int bucket_no)
        {
            int local_depth,hash_index,index,dir_size,i;
            vector<int> temp;
            vector<int>::iterator it;

            local_depth = buckets[bucket_no]->increase_Depth();
            if(local_depth>global_depth)
                grow();

            hash_index = split_hash(bucket_no,local_depth);
            buckets[hash_index] = new Bucket(local_depth);
            temp = buckets[bucket_no]->copy();
            buckets[bucket_no]->clear();
            index = 1<<local_depth;
            dir_size = 1<<global_depth;
            
            for( i=hash_index-index ; i>=0 ; i-=index )
                buckets[i] = buckets[hash_index];
            
            for( i=hash_index+index ; i<dir_size ; i+=index )
                buckets[i] = buckets[hash_index];
            
            for(it=temp.begin();it!=temp.end();it++)
                insert(*it);
        }
        void search(int key)
        {
            int bucket_no = hash(key);
            cout<<"Searching key "<<key<<" in bucket "<<bucket_no<<endl;
            buckets[bucket_no]->search(key);
        }

};

int main()
{   
    int initial_global_depth,count;
    cout<<"Initial global depth : "; 
    cin>>initial_global_depth;
    
    cout<<endl<<"Enter no of values:";
    cin>>count;
    //string choice;

    table t(initial_global_depth);

    // do
    // {
    //     cout<<endl;
    //     cout<<">>> ";
    //     cin>>choice;
    //     if(choice=="insert")
    //     {
    //         cin>>value;
    //         cout<<endl;
    //         t.insert(value);
    //     }
    //     else if(choice=="display")
    //     {
    //         cout<<endl;
    //         t.print();
    //     }
    //     else if(choice=="search")
    //     {
    //         cin>>value;
    //         cout<<endl;
    //         t.search(value);
    //     }
    // } while(choice!="exit");
    int values[count];
    cout<<endl<<"Start entering value: "<<endl;
    for (int i = 0; i < count; ++i)
    {   
        cin>>values[i];
    }
    
    for (int i = 0; i < count; ++i)
    {   
        t.insert(values[i]);
    }
    t.print();

    int key;
    cout<<endl<<"enter search key:";
    cin>>key;
    t.search(key);
    return 0;
}
