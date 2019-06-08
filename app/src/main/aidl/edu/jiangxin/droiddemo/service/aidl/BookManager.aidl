// BookManager.aidl
package edu.jiangxin.droiddemo.service.aidl;

import edu.jiangxin.droiddemo.service.aidl.Book;

interface BookManager {
    List<Book> getBooks();
    void addBook(in Book book);

}
