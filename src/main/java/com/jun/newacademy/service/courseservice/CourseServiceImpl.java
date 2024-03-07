package com.jun.newacademy.service.courseservice;

import com.jun.newacademy.dto.coursedto.CourseRequestDto;
import com.jun.newacademy.dto.coursedto.CourseResponseDto;
import com.jun.newacademy.entity.course.Course;
import com.jun.newacademy.entity.course.CourseCategory;
import com.jun.newacademy.entity.instructor.Instructor;
import com.jun.newacademy.entity.user.UserAuthority;
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

            String category = requestDto.getCategory();

            CourseCategory subject;
            if (category.equals("스프링")) {
                subject = CourseCategory.SPRING;
            } else if (category.equals("노드js")) {
                subject = CourseCategory.NODEJS;
            } else if (category.equals("리액트")) {
                subject = CourseCategory.REACT;
            } else {
                throw new IllegalArgumentException("유효하지 않은 과목입니다.");
            }

            Course course = new Course(requestDto, instructor, subject);

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
            Instructor instructor = instructorOptional.get();

            String category = requestDto.getCategory();

            CourseCategory subject;
            if (category.equals("스프링")) {
                subject = CourseCategory.SPRING;
            } else if (category.equals("노드js")) {
                subject = CourseCategory.NODEJS;
            } else if (category.equals("리액트")) {
                subject = CourseCategory.REACT;
            } else {
                throw new IllegalArgumentException("유효하지 않은 과목입니다.");
            }

            course.setTitle(title);
            course.setPrice(price);
            course.setSyllabus(syllabus);
            course.setCategory(subject);
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
