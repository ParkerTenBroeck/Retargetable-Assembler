package org.parker.retargetableassembler.pipe.util.iterators;

import java.util.Iterator;
import java.util.Stack;

public class IteratorStack<T> extends PeekBehindIteratorAbstract<T> implements PeekEverywhereIterator<T>{

    private Stack<PeekEverywhereIterator<T>> iteratorStack = new Stack<>();
    private int maxScannerStackSize = 32;


    public void setMaxScannerStackSize(int size){
        if(size < iteratorStack.size()){
            throw new IndexOutOfBoundsException("new size exceeds current size");
        }
        maxScannerStackSize = size;
    }

    public int getMaxScannerStackSize(){
        return maxScannerStackSize;
    }

    public PeekEverywhereIterator<T> peek_iterator_stack(){
        return iteratorStack.peek();
    }

    public void push_iterator_stack(Iterator<T> iterator){
        if(iteratorStack.size() >= maxScannerStackSize){
            throw new IndexOutOfBoundsException("max stack size reached");
        }
        if(iterator instanceof PeekEverywhereIterator){
            iteratorStack.push((PeekEverywhereIterator<T>) iterator);
        }else{
            iteratorStack.push(new PeekEverywhereIteratorWrapper<>(iterator));
        }
    }

    @Override
    public boolean hasNext() {
        return !iteratorStack.empty();
    }

    @Override
    protected T next_peekless() {
        while((!iteratorStack.empty()) && (!iteratorStack.peek().hasNext())){
            onPop(iteratorStack.pop());
        }
        if(!iteratorStack.empty()){
            T temp = iteratorStack.peek().next();
            while(!iteratorStack.empty() && !iteratorStack.peek().hasNext()){
                onPop(iteratorStack.pop());
            }
            return temp;
        }else{
            return null;
        }
    }

    public int getCurrentStackSize(){
        return iteratorStack.size();
    }

    protected void onPop(Iterator<T> popped){

    }

    @Override
    public void setMaxLookAhead(int max) {
        if(!iteratorStack.empty())
            iteratorStack.peek().setMaxLookAhead(max);
    }

    @Override
    public int getMaxLookAhead() {
        if(iteratorStack.empty()){
            return -1;
        }else{
            return iteratorStack.peek().getMaxLookAhead();
        }
    }

    @Override
    public T peek_ahead(int amount) {
        if(iteratorStack.empty()){
            return null;
        }else{
            return iteratorStack.peek().peek_ahead(amount);
        }
    }
}
