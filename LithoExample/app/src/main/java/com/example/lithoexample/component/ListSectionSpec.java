package com.example.lithoexample.component;

import android.graphics.Color;

import com.facebook.litho.sections.Children;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.annotations.GroupSectionSpec;
import com.facebook.litho.sections.annotations.OnCreateChildren;
import com.facebook.litho.sections.common.SingleComponentSection;

@GroupSectionSpec
class ListSectionSpec {

    @OnCreateChildren
    static Children onCreateChildren(final SectionContext c) {
        Children.Builder builder = Children.create();
        for (int i = 0; i < 100; i++) {
            builder.child(SingleComponentSection.create(c)
                    .key(String.valueOf(i))
                    .component(ListItem.create(c)
                            .title(i + ".Hello world")
                            .subTitle("Litho example")
                            .color(i % 2 == 0 ? Color.WHITE : Color.LTGRAY)
                            .build())
            );
        }
        return builder.build();
    }
}
