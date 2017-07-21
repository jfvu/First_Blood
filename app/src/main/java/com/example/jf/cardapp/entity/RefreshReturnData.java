package com.example.jf.cardapp.entity;

import java.util.List;

/**
 * Created by jf on 2017/2/28.
 */

public class RefreshReturnData {
    /**
     * Data : {"TotalCount":1,"CardList":[{"ID":1,"Name":"sample string 2","SetsName":"sample string 3"},{"ID":1,"Name":"sample string 2","SetsName":"sample string 3"}]}
     * ErrorCode : 0
     * Message :
     */

    private DataBean Data;
    private int ErrorCode;
    private String Message;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public static class DataBean {
        /**
         * TotalCount : 1
         * CardList : [{"ID":1,"Name":"sample string 2","SetsName":"sample string 3"},{"ID":1,"Name":"sample string 2","SetsName":"sample string 3"}]
         */

        private int TotalCount;
        private List<CardListBean> CardList;

        public int getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(int TotalCount) {
            this.TotalCount = TotalCount;
        }

        public List<CardListBean> getCardList() {
            return CardList;
        }

        public void setCardList(List<CardListBean> CardList) {
            this.CardList = CardList;
        }

        public static class CardListBean {
            /**
             * ID : 1
             * Name : sample string 2
             * SetsName : sample string 3
             */

            private int ID;
            private String Name;
            private String SetsName;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getSetsName() {
                return SetsName;
            }

            public void setSetsName(String SetsName) {
                this.SetsName = SetsName;
            }
        }
    }
}
