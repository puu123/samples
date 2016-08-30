package foo;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.reflect.ClassPath;

import static java.time.temporal.TemporalAdjusters.*;

public class ClassMain {

	public static void main(String[] args) throws Exception {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Set<Class<?>> allClasses = ClassPath.from(loader)
		        //.getTopLevelClasses("foo").stream()
		        .getAllClasses().stream()
		        .map(info -> info.load())
		        .collect(Collectors.toSet());
		System.out.println(allClasses.size());
		
		{
			OffsetDateTime dateTime = OffsetDateTime.now();
			System.out.println(dateTime);
			long a =dateTime.toEpochSecond();
			System.out.println(a);
			System.out.println(System.currentTimeMillis()/1000);
		}
		
		Date d = new Date();
		System.out.println(d);
		{
			Calendar calendar = Calendar.getInstance();
			calendar.set(2016, 8, 1, 0, 0, 0);
			System.out.println(calendar.getTime());
		}
		{
			OffsetDateTime dateTime = OffsetDateTime
				.of(2016, 8, 1, 0, 0, 0, 0, ZoneOffset.ofHours(9));
			System.out.println(dateTime);
		}
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		{
			Calendar calendar = Calendar.getInstance();
			calendar.set(2016, 8, 1, 0, 0, 0);
			System.out.println("s:"+s.format(calendar.getTime()));
			calendar.add(Calendar.YEAR, 1);
			System.out.println("1:"+s.format(calendar.getTime()));
			calendar.add(Calendar.MONTH, -2);
			System.out.println("2:"+s.format(calendar.getTime()));
			System.out.println(calendar.getTime());
			System.out.println("e:"+s.format(calendar.getTime()));
			
			ZonedDateTime dateTime = ZonedDateTime
					.of(2016, 8, 1, 0, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
			dateTime = dateTime.plusYears(1).minusMonths(2);
			System.out.println(dateTime);
		}
		{
			Calendar calendar = Calendar.getInstance();
			calendar.set(2015, 8, 1, 0, 0, 0);
			System.out.println("["+calendar.getTime()+"] isLeapYear:"+isLeapYear(calendar.getTime()));
		}
		{
			ZonedDateTime dateTime = ZonedDateTime
				.of(2015, 8, 1, 0, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
			System.out.println("["+dateTime+"] isLeapYear:"+isLeapYear(dateTime));
		}
		{
			//Locale.JAPAN
			//Locale.JAPANESE ではなく
			Locale locale = new Locale("ja", "JP", "JP");
			Calendar calendar = 
					//Calendar.getInstance(locale);//　あれ?　うまくいかない...あ、西暦年ではないのか?
					Calendar.getInstance();
			calendar.set(2016, 8, 1, 0, 0, 0);
			SimpleDateFormat format = new SimpleDateFormat("GGGG yy年MM月dd日",locale);
			System.out.println(format.format(calendar.getTime()));	
		}
		{
			
			JapaneseDate japaneseDate = JapaneseDate.of(JapaneseEra.HEISEI, 28, 8, 1);
			System.out.println(japaneseDate);
			DateTimeFormatter formatter = new DateTimeFormatterBuilder()
					.appendPattern("G yy年MM月dd日")
					.toFormatter(Locale.JAPANESE)
					.withResolverStyle(ResolverStyle.STRICT);
			System.out.println(formatter.format(japaneseDate));
		}
		{
			
			 LocalDate today = LocalDate.of(2016,8,3); 
			 // 2016-08-01 
			 System.out.println(today.with(java.time.temporal.TemporalAdjusters.firstDayOfMonth())); 
			 // 2016-08-31 
			 System.out.println(today.with(lastDayOfMonth())); 
			 // 2016-08-08
			 System.out.println(today.with(next(DayOfWeek.MONDAY)));
			
		}
		
		
		{
			System.out.println(MonthDay.of(8, 3));
			System.out.println(System.currentTimeMillis());
			System.out.println(Clock.systemUTC().millis());
			GregorianCalendar gcalendar = new GregorianCalendar();
			System.out.println(gcalendar.getTime());
			Calendar calendar = Calendar.getInstance();
			Instant instant = calendar.toInstant();
			OffsetDateTime dateTime = instant.atOffset(ZoneOffset.ofHours(9));
			System.out.println(dateTime);
			Date date = Date.from(dateTime.toInstant());
			
			LocalDateTime start = LocalDateTime.of(2016, 8, 3,  9, 0);
			LocalDateTime end   = LocalDateTime.of(2016, 8, 3, 18, 0);
			Duration duration = Duration.between(start, end);
			System.out.println(duration);
		}
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss[XXX['['VV']']]");
			LocalDateTime  ldt = LocalDateTime.of(2016, 8, 1, 9, 0);
			OffsetDateTime odt = OffsetDateTime.of(2016, 8, 1, 9, 0, 0, 0, ZoneOffset.ofHours(9));
			ZonedDateTime  zdt = ZonedDateTime.of(2016, 8, 1, 9, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
			System.out.println(formatter.format(ldt));
			System.out.println(formatter.format(odt));
			System.out.println(formatter.format(zdt));
			
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX'['VV']'");
			//System.out.println(formatter2.format(ldt));
			// UnsupportedTemporalTypeException
		}
		
		{
			
			DateTimeFormatter formatter = 
					new DateTimeFormatterBuilder()
						.appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
						.appendLiteral('-')
						.appendValue(ChronoField.MONTH_OF_YEAR, 2, 2, SignStyle.NORMAL)
						.appendLiteral('-')
						.appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NEVER)
						.appendLiteral('T')
						.appendValue(ChronoField.HOUR_OF_DAY, 1, 2, SignStyle.NEVER)
						.appendLiteral(':')
						.appendValue(ChronoField.MINUTE_OF_HOUR, 1, 2, SignStyle.NEVER)
						.appendLiteral(':')
						.appendValue(ChronoField.SECOND_OF_MINUTE, 1, 2, SignStyle.NEVER)
						.toFormatter();

			LocalDateTime ldt2 = LocalDateTime.parse("2016-8-1T1:2:3", formatter); 
			System.out.println(ldt2);
			System.out.println(LocalDateTime.now().format(formatter));
			//LocalDateTime ldt1 = LocalDateTime.parse("2016-8-1T1:2:3");
			//java.time.format.DateTimeParseException: Text '2016-8-1T1:2:3' could not be parsed at index 5
		}{
			
			DateTimeFormatter formatter =
					DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
					.withResolverStyle(ResolverStyle.STRICT);
			//LocalDateTime ld = LocalDateTime.parse("2016-08-01T01:00:00.123",formatter);
			
			
		}
		
		
	}
	
	public static boolean isLeapYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		GregorianCalendar calendar2 = new GregorianCalendar();
		return calendar2.isLeapYear(calendar.get(Calendar.YEAR));
	}
	
	public static boolean isLeapYear(ZonedDateTime dateTime) {
		return Year.from(dateTime).isLeap();
	}
}
