package com.example.laptop88.model;

public class HistorySearch {
    int idHistorySearch;
    int idAccount;
    String searchContent;

    public HistorySearch(int idHistorySearch, int idAccount, String searchContent) {
        this.idHistorySearch = idHistorySearch;
        this.idAccount = idAccount;
        this.searchContent = searchContent;
    }

    public int getIdHistorySearch() {
        return idHistorySearch;
    }

    public void setIdHistorySearch(int idHistorySearch) {
        this.idHistorySearch = idHistorySearch;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
}
