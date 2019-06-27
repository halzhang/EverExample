package com.example.lithoexample.component;

import android.graphics.Color;

import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

@LayoutSpec
class ListItemSpec {

    @OnCreateLayout
    static Component onCreateLayout(ComponentContext cc, @Prop int color, @Prop String title, @Prop String subTitle) {
        return Column.create(cc)
                .paddingDip(YogaEdge.ALL, 16)
                .backgroundColor(color)
                .child(Text.create(cc)
                        .text(title)
                        .textSizeSp(40))
                .child(Text.create(cc)
                        .text(subTitle)
                        .textSizeSp(20))
                .build();
    }

}
