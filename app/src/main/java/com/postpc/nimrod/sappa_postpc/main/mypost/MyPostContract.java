package com.postpc.nimrod.sappa_postpc.main.mypost;

import android.os.Bundle;

public interface MyPostContract {

    interface View{

        Bundle getPostArguments();
    }

    interface Presenter{

        void init();
    }

}
