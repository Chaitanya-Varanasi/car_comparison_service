package org.example.factory;

import org.example.constants.CarSortAlgorithmType;
import org.example.dao.impl.InMemoryCarDao;
import org.example.service.comparison.CarSortAlgorithm;
import org.example.service.comparison.impl.ScoreBasedCarSortAlgorithm;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;

import javax.ws.rs.NotFoundException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


public class CarSortAlgorithmFactoryTest {

//    @InjectMocks
//    private CarSortAlgorithmFactory carSortAlgorithmFactory;
//
//    @BeforeMethod
//    private void init() {
//        MockitoAnnotations.initMocks(this);
//        carSortAlgorithmFactory.initCarSortAlgorithmFactory(Collections.singletonList(new ScoreBasedCarSortAlgorithm(new InMemoryCarDao())));
//    }
//
//    @Test
//    void getCarSortAlgorithm_Found() {
//        CarSortAlgorithm result = carSortAlgorithmFactory.getCarSortAlgorithm(CarSortAlgorithmType.SCORE_BASED);
//        assertTrue(result instanceof ScoreBasedCarSortAlgorithm);
//    }
//
//    @Test
//    void getCarSortAlgorithm_NotFound() {
//
//        NotFoundException exception = assertThrows(NotFoundException.class,
//                () -> carSortAlgorithmFactory.getCarSortAlgorithm(CarSortAlgorithmType.ML));
//
//        assertEquals("CarSortAlgorithm not found for getCarSortAlgorithm : null", exception.getMessage());
//    }
}
