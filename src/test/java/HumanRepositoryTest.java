import com.lenDB.repository.Human;
import com.lenDB.repository.HumanRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;

/**
 * Created by elena on 20.06.2017.
 */
public class HumanRepositoryTest {
    private HumanRepository humanRepository=new HumanRepository();
    private Human human1;
    private Human human2;

@Before
    public void InitDB(){
    human1=new Human(UUID.randomUUID().toString(),"Ivan'","Petrov");
    human2=new Human(UUID.randomUUID().toString(),"Petr","Petrov");

    humanRepository.save(human1);
    humanRepository.save(human2);


}

@After
    public void Clear(){
        humanRepository.deleteAll();

}

@Test
    public  void UpdateHuman(){
        String newName ="Igor";
        human1.setFirstName(newName);
        String id=human1.getId();
        humanRepository.save(human1);
        Human savedHuman=humanRepository.findById(id);
        Assert.assertThat(savedHuman.getFirstName(),is(newName));


}

@Test
    public void SelectHuman(){
List<Human> humans=humanRepository.findAll();
    Assert.assertNotNull(humans);
    Assert.assertThat(humans.size(),greaterThan(0));
    Assert.assertThat(humans.size(),is(2));

}




}
