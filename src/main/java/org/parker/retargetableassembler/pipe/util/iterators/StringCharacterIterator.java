package org.parker.retargetableassembler.pipe.util.iterators;

public class StringCharacterIterator implements PeekEverywhereIterator<Character> {

    private final String data;
    private int index = 0;

    public StringCharacterIterator(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.substring(index);
    }

    @Override
    public boolean hasNext() {
        return index < data.length();
    }

    @Override
    public Character next() {
        if (hasNext()) return data.charAt(index++);
        return 0;
    }

    @Override
    public void setMaxLookAhead(int max) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxLookAhead() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Character peek_ahead(int amount) {
        if (amount + index < data.length()) {
            return data.charAt(amount + index);
        } else {
            return 0;
        }
    }

    @Override
    public void setMaxLookBehind(int max, Character fill) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxLookBehind() {
        return -1;
    }

    @Override
    public Character peek_behind(int amount) {
        if (amount + index > 0) {
            return data.charAt(index - amount);
        } else {
            return 0;
        }
    }

    @Override
    public void fill_peek_behind(Character fill) {
        throw new UnsupportedOperationException();
    }
}
