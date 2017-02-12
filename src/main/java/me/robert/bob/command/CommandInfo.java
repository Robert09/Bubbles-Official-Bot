package me.robert.bob.command;

import java.lang.annotation.*;

/**
 * Created by O3Bubbles09 on 2/4/2017
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    String description();

    String usage();

    String[] aliases();

    boolean needsMod() default false;
}
