// IBookManager.aidl
package edu.jiangxin.droiddemo.aidl;

import edu.jiangxin.droiddemo.aidl.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);

}
