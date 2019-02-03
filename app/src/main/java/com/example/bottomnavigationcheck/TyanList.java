package com.example.bottomnavigationcheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TyanList extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Tyan> objects;

    TyanList(Context context, ArrayList<Tyan> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Tyan p = getTyan(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.tvName)).setText(p.getName());
        ((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.getIcon());

        // присваиваем чекбоксу обработчик
        // пишем позицию
        return view;
    }

    // товар по позиции
    Tyan getTyan(int position) {
        return ((Tyan) getItem(position));
    }

    public void addTyan(String name, int icon, int id, boolean was) {
        Tyan nw = new Tyan(name, icon, id,true);
        objects.add(nw);
    }

}
