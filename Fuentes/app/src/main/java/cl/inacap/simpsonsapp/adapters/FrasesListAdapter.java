package cl.inacap.simpsonsapp.adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

import cl.inacap.simpsonsapp.R;
import cl.inacap.simpsonsapp.dto.Frase;


//Listview que muestra los datos obtenidos de la api rest
public class FrasesListAdapter extends ArrayAdapter<Frase> {

    private List<Frase> frases;
    private Activity activity;

    public FrasesListAdapter(@NonNull Activity context, int resource, @NonNull List<Frase> objects) {
        super(context, resource, objects);
        this.frases = objects;
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = this.activity.getLayoutInflater();
        View item = inflater.inflate(R.layout.frases_list,null,true);
        TextView fraseTxt = item.findViewById(R.id.frase_personaje);
        TextView nombreTxt = item.findViewById(R.id.nombre_personaje);
        ImageView imagenTxt = item.findViewById(R.id.imagen_personaje);

        fraseTxt.setText("\""+ this.frases.get(position).getQuote() + "\"");
        nombreTxt.setText("-" + this.frases.get(position).getCharacter());
        Picasso.get().load(this.frases.get(position).getImage())
                .resize(300,600).centerInside().into(imagenTxt);
        return item;

    }
}
