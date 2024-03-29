package com.jun.newacademy.service.instructorservice;

import com.jun.newacademy.model.dto.instructordto.InstructorRequestDto;
import com.jun.newacademy.model.dto.instructordto.InstructorResponseDto;
import com.jun.newacademy.model.entity.instructor.Instructor;
import com.jun.newacademy.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    @Override
    @Transactional
    public InstructorResponseDto save(InstructorRequestDto requestDto) {
        Instructor instructor = new Instructor(requestDto);
        instructorRepository.save(instructor);

        return new InstructorResponseDto(instructor);
    }

    @Override
    public InstructorResponseDto find(Long id) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(id);

        if (instructorOptional.isPresent()) {
            Instructor instructor = instructorOptional.get();
            return new InstructorResponseDto(instructor);
        }

        throw new IllegalArgumentException("유효하지 않은 강사 ID입니다.");
    }

    @Override
    @Transactional
    public InstructorResponseDto update(Long id, InstructorRequestDto requestDto) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(id);

//        String name = requestDto.getName();
        String career = requestDto.getCareer();
        String company = requestDto.getCompany();
        String phone = requestDto.getPhone();
        String introduce = requestDto.getIntroduce();

        if (instructorOptional.isPresent()) {
            Instructor instructor = instructorOptional.get();

//            instructor.setName(name);
            instructor.setCareer(career);
            instructor.setCompany(company);
            instructor.setPhone(phone);
            instructor.setIntroduce(introduce);

            return new InstructorResponseDto(instructor);
        }

        throw new IllegalArgumentException("잘못된 수정 정보입니다.");
    }
}
