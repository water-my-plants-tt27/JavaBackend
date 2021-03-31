package com.watermyplants.demo.services;

import com.watermyplants.demo.exceptions.ResourceNotFoundException;
import com.watermyplants.demo.models.Plant;
import com.watermyplants.demo.models.User;
import com.watermyplants.demo.repositories.PlantRepository;
import com.watermyplants.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "plantService")
public class PlantServiceImpl implements PlantService
{

    @Value("${twilio.trial-number.path}")
    private String trialNumber;

    @Value("$twilio.destination-number.path")
    private String destinationNumber;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PlantRepository plantRepository;

    @Override
    public List<Plant> findAll()
    {
        List<Plant> list = new ArrayList<>();
        plantRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Plant findPlantById(long id)
    {
        return plantRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (plantRepository.findById(id).isPresent())
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (plantRepository.findById(id).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()))
            {
                plantRepository.deleteById(id);
            }
            else
            {
                throw new ResourceNotFoundException(Long.toString(id) + " " + authentication.getName());
            }
        }
        else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Plant save(Plant Plant)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //        Plant.setUser(userRepository.findByUsername(authentication.getName()));
        User currentUser = userRepository.findByUsername(authentication.getName());
        Plant savePlant =  plantRepository.save(Plant);
        smsSender.sendSms(new SmsRequest(destinationNumber, "Your watering schedule now includes: " + savePlant.getName()));
        return savePlant;
    }

    @Override
    public List<Plant> findByUserName(String username)
    {
        List<Plant> list = new ArrayList<>();
        plantRepository.findAll().iterator().forEachRemaining(list::add);

        list.removeIf(q -> !q.getUser().getUsername().equalsIgnoreCase(username));
        return list;
    }
}
