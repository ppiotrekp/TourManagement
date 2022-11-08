package pl.ppyrczak.busschedulesystem.service;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.exception.ApiRequestException;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.registration.email.EmailSender;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ReviewRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;
import pl.ppyrczak.busschedulesystem.repository.UserRepository;
import pl.ppyrczak.busschedulesystem.service.logic.Constraint;
import pl.ppyrczak.busschedulesystem.service.subscription.Subscriber;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScheduleService implements Subscriber {
    private final ScheduleRepository scheduleRepository;
    private final PassengerRepository passengerRepository;
    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;
    private final EmailSender emailSender;

    private final Constraint constraint;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           PassengerRepository passengerRepository,
                           ReviewRepository reviewRepository,
                           UserRepository userRepository, EmailSender emailSender,
                           Constraint constraint) {
        this.scheduleRepository = scheduleRepository;
        this.passengerRepository = passengerRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.emailSender = emailSender;
        this.constraint = constraint;
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).
                orElseThrow(() -> new ApiRequestException(
                        "Schedule with id " + id + " does not exist"));
    }

    public List<Schedule> getSchedules(Schedule schedule) {
        Schedule scheduleToFind = Schedule.builder()
                .departureFrom(schedule.getDepartureFrom())
                .departureTo(schedule.getDepartureTo())
                .departure(schedule.getDeparture())
                .arrival(schedule.getArrival())
                .build();

        return scheduleRepository.findAll(Example.of(scheduleToFind));
    }

    public Schedule addSchedule(Schedule schedule) {
        if (!constraint.isBusAvaliable(schedule)) {
            throw new RuntimeException("Bus is not available");
        }
        List<ApplicationUser> users = userRepository.findAll();

        List<ApplicationUser> subscribers = users.stream()
                .filter(user -> user.getSubscribed() == true)
                .collect(Collectors.toList());

        sendInfoAboutNewTrip(schedule, subscribers);
        return scheduleRepository.save(schedule);
    }

    public Schedule editSchedule(Schedule scheduleToUpdate, Long id) {

        if (scheduleRepository.findById(id).isPresent()) {
            Schedule originalSchedule = scheduleRepository.getById(id);
            if (originalSchedule.getArrival().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("You can not change finished schedule");
            }
        }

        if (scheduleToUpdate.getArrival().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("You can not set arrival in the past");
        }

        if (scheduleToUpdate.getArrival().isBefore(scheduleToUpdate.getDeparture())) {
            throw new RuntimeException("Arrival can not be before departure");
        } //todo obsluzyc wyjatki

        return scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setBusId(scheduleToUpdate.getBusId());
                    schedule.setDepartureFrom(scheduleToUpdate.getDepartureFrom());
                    schedule.setDepartureTo(scheduleToUpdate.getDepartureTo());
                    schedule.setDeparture(scheduleToUpdate.getDeparture());
                    schedule.setArrival(scheduleToUpdate.getArrival());
                    schedule.setTicketPrice(scheduleToUpdate.getTicketPrice());
                    return scheduleRepository.save(schedule);
                })
                .orElseGet(() -> {
                    return addSchedule(scheduleToUpdate);
                });
    }

    public void deleteSchedule(Long id) {
        if (!scheduleRepository.findById(id).isPresent()) {
            throw new ApiRequestException("Schedule with id " + id + " does not exist");
        }
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> getSchedulesWithPassengersAndReviews() {
        List<Schedule> allSchedules = scheduleRepository.findAll();
        List<Long> ids = allSchedules.stream()
                .map(Schedule::getId)
                .collect(Collectors.toList());
        List<Passenger> passengers = passengerRepository.
                findAllByScheduleIdIn(ids);
        List<Review> reviews = reviewRepository.
                findAllByScheduleIdIn(ids);

        allSchedules.forEach(schedule -> schedule.
                setPassengers(extractPassengers(passengers, schedule.getId())));

        allSchedules.forEach(schedule -> schedule.
                setReviews(extractReviews(reviews, schedule.getId())));

        return allSchedules;
    }

    private List<Passenger> extractPassengers(List<Passenger> passengers, Long id) {
        return passengers.stream()
                .filter(passenger -> Objects.equals(passenger.getScheduleId(), id))
                .collect(Collectors.toList());
    }

    private List<Review> extractReviews(List<Review> reviews, Long id) {
        return reviews.stream()
                .filter(review -> Objects.equals(review.getScheduleId(), id))
                .collect(Collectors.toList());
    }

    @Override
    public void sendInfoAboutNewTrip(Schedule schedule, List<ApplicationUser> users) {
        for (ApplicationUser user : users) {
            emailSender.send(user.getUsername(),
                            buildEmail(user.getFirstName(), schedule));
        }
    }
    
    private String buildEmail(String name,  Schedule schedule) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">New trip !</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> We have a new trip in our offer. This time the destination is " + schedule.getDepartureTo() + ". " +
                "<p> The departure is from " + schedule.getDepartureFrom() + ". It will take place on " + schedule.getDeparture() + ". " +
                " <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
