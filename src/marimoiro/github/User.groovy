package marimoiro.github

import groovy.transform.InheritConstructors

@InheritConstructors
public class User extends Person
{

    public String getBio()
    {
        return raw.bio
    }

}