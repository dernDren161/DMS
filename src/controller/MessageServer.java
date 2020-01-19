package controller;

import model.Message;

import java.util.*;

public class MessageServer implements  Iterable<Message>{
    private Map<Integer, List<Message>> messages;
    private List<Message> selected;

    public MessageServer(){
        selected = new ArrayList<Message>();
        messages = new TreeMap<Integer, List<Message>>();
        List<Message> list = new ArrayList<Message>();
        list.add(new Message("Title-1","No Messages Yet Bro Sorry"));
        list.add(new Message("Title-2","No Messages Yet Again Bro LOL"));
        messages.put(0,list);

        list = new ArrayList<Message>();
        list.add(new Message("Title-3","Not Even Now myann"));
        messages.put(1,list);
    }

    public void setSelectedServers(Set<Integer> servers){
        selected.clear();
        for(Integer id: servers){
            if(messages.containsKey(id)){
                selected.addAll(messages.get(id));
            }
        }
    }

    public int getMessageCount(){
        return selected.size();// Number of messages waiting to be downloaded.
    }

    @Override
    public Iterator<Message> iterator() {
        return new MessageIterator(selected);
    }
}

class MessageIterator implements Iterator{
    private Iterator<Message> iterator;

    public MessageIterator(List<Message> messages){
        iterator = messages.iterator();
    }
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Object next() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}