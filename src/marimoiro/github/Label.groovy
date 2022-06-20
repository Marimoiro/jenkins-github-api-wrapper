package marimoiro.github

import java.util.Date

import org.kohsuke.github.GHLabel

public class Label implements Serializable
{

  GHLabel label

  public Label(GHLabel label)
  {
    this.label = label
  }

  public GHLabel getLabel(){
    return this.label
  }

  public GHLabel getRaw(){ // this libraries rule
    return this.label
  }


  
  /* Detail */

  public String getName()
  {
    return label.name
  }
  
  public void setName(String name)
  {
    label.setName(name)
  }
  
  public String getColor()
  {
    return label.color
  }
  
  public void setColor(String color)
  {
    label.setColor(color)
  }
  
  public String getDescription()
  {
    return label.description
  }
  
  public void setDescription(String description)
  {
    label.setDescription(description)
  }
  
  /* delete */
  public void delete()
  {
    label.delete()
  }
  

  private void writeObject(java.io.ObjectOutputStream out)
  {
    GitHubClient.write(out,label)
  }

  private void readObject(java.io.ObjectInputStream inStream)
  {
    label = GitHubClient.read(inStream,Label.class)
  }
  
}
