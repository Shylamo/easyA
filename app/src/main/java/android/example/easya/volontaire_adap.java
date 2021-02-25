package android.example.easya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class volontaire_adap extends ArrayAdapter<volontaire> {

    private Context mcontext;
    private  int mressource;

    public volontaire_adap(@NonNull Context context, int resource, @NonNull ArrayList<volontaire> objects) {
        super(context, resource, objects);
        this.mcontext=context;
        this.mressource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= LayoutInflater.from(mcontext);
        convertView= layoutInflater.inflate(mressource,parent,false);
        ImageView imageView=convertView.findViewById(R.id.profile_image);
        TextView name = convertView.findViewById(R.id.user_name);
        TextView niveau=convertView.findViewById(R.id.user_niveau);
        TextView mail=convertView.findViewById(R.id.user_mail);
        imageView.setImageResource(getItem(position).getImage());

        name.setText(getItem(position).getNom());
        niveau.setText(getItem(position).getNiveau());
        mail.setText(getItem(position).getMail());

        return convertView;
    }
}
