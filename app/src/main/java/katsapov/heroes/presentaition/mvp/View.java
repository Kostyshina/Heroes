package katsapov.heroes.presentaition.mvp;

import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import katsapov.heroes.R;
import katsapov.heroes.data.entitiy.Hero;

public class View implements HeroContract.HeroView {
    @Override
    public void updateHeroesList(List<Hero> heroesList) {

    }

    @Override
    public void showIsLoading(Boolean isLoading) {

    }

    @Override
    public void showHeroDetails(Hero hero) {
        Hero obj = hero;
        //final Dialog dialog = new Dialog();
        final Dialog dialog = null;
        dialog.setContentView(R.layout.dialog_fragment);
        dialog.setTitle("О персонаже");

        TextView tvCulture = dialog.findViewById(R.id.tvCulture);
        tvCulture.setText(obj.getCulture());

        TextView tvGender = dialog.findViewById(R.id.tvGender);
        tvGender.setText(obj.getGender());

        TextView tvBorn = dialog.findViewById(R.id.tvBorn);
        tvBorn.setText(obj.getBorn());

        TextView tvDie = dialog.findViewById(R.id.tvDie);
        tvDie.setText(obj.getDie());

        TextView tvUrl = dialog.findViewById(R.id.tvUrl);
        tvUrl.setText(obj.getUrl());

        TextView tvFather = dialog.findViewById(R.id.tvFather);
        tvFather.setText(obj.getFather());

        TextView tvMother = dialog.findViewById(R.id.tvMother);
        tvMother.setText(obj.getMother());

        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
