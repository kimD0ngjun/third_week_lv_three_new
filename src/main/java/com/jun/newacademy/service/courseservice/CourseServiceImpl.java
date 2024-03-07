package com.jun.newacademy.service.courseservice;

import com.jun.newacademy.dto.coursedto.CourseRequestDto;
import com.jun.newacademy.dto.coursedto.CourseResponseDto;
import com.jun.newacademy.entity.course.Course;
import com.jun.newacademy.entity.instructor.Instructor;
import com.jun.newacademy.repository.CourseRepository;
import com.jun.newacademy.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public CourseResponseDto save(CourseRequestDto requestDto) {
        Long instructorId = requestDto.getInstructorId();

        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);

        if (instructorOptional.isPresent()) {
            Instructor instructor = instructorOptional.get();
            Course course = new Course(requestDto, instructor);

            courseRepository.save(course);
            return CourseResponseDto.fromEntity(course);
        }

        return null;
    }

    @Override
    public CourseResponseDto findById(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            return CourseResponseDto.fromEntity(course);
        }

        return null;
    }

    @Override
    @Transactional
    public CourseResponseDto update(Long id, CourseRequestDto requestDto) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        Optional<Instructor> instructorOptional = instructorRepository.findById(requestDto.getInstructorId());

        if (courseOptional.isPresent() && instructorOptional.isPresent()) {
            Course course = courseOptional.get();

            String title = requestDto.getTitle();
            String price = requestDto.getPrice();
            String syllabus = requestDto.getSyllabus();
            String category = requestDto.getCategory();
            Instructor instructor = instructorOptional.get();

            course.setTitle(title);
            course.setPrice(price);
            course.setSyllabus(syllabus);
            course.setCategory(category);
            course.setInstructor(instructor);

            return CourseResponseDto.fromEntity(course);
        }

        return null;
    }

    @Override
    public List<CourseResponseDto> findByCategory(String category) {
        List<Course> list = courseRepository.findByCategory(category);

        return list.stream().map(CourseResponseDto::fromEntity).toList();
    }

    @Override
    public List<CourseResponseDto> findByInstructor(Long instructorId) {
        List<Course> list = courseRepository.findByInstructorId(instructorId);

        return list.stream().map(CourseResponseDto::fromEntity).toList();
    }
}
