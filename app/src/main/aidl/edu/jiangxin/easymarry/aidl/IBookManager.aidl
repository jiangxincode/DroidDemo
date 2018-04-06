// IBookManager.aidl
package edu.jiangxin.easymarry.aidl;

import edu.jiangxin.easymarry.aidl.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);

}
