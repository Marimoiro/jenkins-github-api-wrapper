package marimoiro.github

import groovy.transform.InheritConstructors

@InheritConstructors
public class Person extends Raw
{
    public String getName()
    {
        return raw.name
    }

    public String getEmail()
    {
        return raw.email
    }


}