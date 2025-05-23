
    Node* addTwo(Node* ll1, Node* ll2) {
         Node* head = new Node(); 
        Node* curr = head; 
        int carry = 0;
        while(ll1 || ll2 || carry){
            int sum = carry;
            if(ll1){
                sum+= ll1->val;
                ll1 = ll1->val;
            }
            if(ll2){
                sum += ll2->val;
                ll2 = ll2->val;
            }
            
            carry = sum / 10;   
            curr->next = new Node(sum%10);
            curr = curr->next;
            
        }
        
        
        
        return head->next;
}
