import { Ionicons } from "@expo/vector-icons";
import * as WebBrowser from "expo-web-browser";
import * as React from "react";
import { StyleSheet, Text, View, Modal } from "react-native";
import DateRangePicker from "../components/DateRangePicker";
import {
  RectButton,
  ScrollView,
  TouchableOpacity,
  FlatList,
} from "react-native-gesture-handler";
import { CalendarList } from "react-native-calendars";
import { makeid } from "../utils/helpers";

export default function QuarantineScreen() {
  const [selectedDays, setSelectedDays] = React.useState([
    { start: "2020-04-12", end: "2020-04-19", key: "abcd1234" },
  ]);
  const CALENDAR = "CALENDAR";
  const DATE_SELECTOR = "DATE_SELECTOR";
  const [currentScreen, setCurrentScreen] = React.useState(CALENDAR);


  const getQuarantineMarkedDays = (startDate, endDate, markedDates) => {
    let deltaDays =
      (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
    let start = new Date(startDate);
    for (var i = 0; i <= deltaDays; i++) {
      start.setDate(startDate.getDate() + i);

      markedDates[formatDate(start)] = {
        marked: true
      };
    }
    return markedDates;
  };


  const formatDate = (date, type) => {
    let dt = zeroPadNumber(date.getDate())
    let month = zeroPadNumber(date.getMonth() + 1)

    if (type == "IN")
      return (
        "" + dt + "-" + month + "-" + date.getFullYear()
      );
    else
      return date.getFullYear() + "-" + month + "-" + dt;
  };

  const zeroPadNumber = (number) => {
    return number < 10 ? "0" + number : "" + number;
  }

  const getMarkedDaysObject = (selectedDays) => {
    let markedDates = {};
    selectedDays.forEach(({ start, end }) => {
      let startDate = new Date(Date.parse(start));
      let endDate = new Date(Date.parse(end));

      markedDates = getQuarantineMarkedDays(startDate, endDate, markedDates);
    });
    return markedDates;
  }

  let markedDays = getMarkedDaysObject(selectedDays);
  return (
    <View
      style={styles.container}
      contentContainerStyle={styles.contentContainer}
    >
      <ScreenHeader
        label="MY CALENDAR"
        iconName="md-add"
        onClick={() => {
          setCurrentScreen(
            currentScreen === CALENDAR ? DATE_SELECTOR : CALENDAR
          );
        }}
      />
      <Divider marginTop={12} />
      <View
        style={{
          marginTop: 32,
          flexDirection: "row",
          justifyContent: "center",
          flexWrap: "wrap",
        }}
      >
        <OptionButton label="Quarantine" />
      </View>
      <View style={{ marginTop: 14 }}>
        {currentScreen === CALENDAR ? (
          <CalendarList
            pastScrollRange={0}
            futureScrollRange={4}
            current={Date()}
            scrollEnabled={true}
            minDate={Date()}
            dayComponent={({ marking, date, state }) => {
              return (
                <View
                  style={{
                    borderWidth: 1,
                    borderRadius: 4,
                    borderColor: "#818181",
                    backgroundColor:
                      marking instanceof Array ? "transparent" : "#FFCEDF",
                    opacity: state === "disabled" ? 0.4 : 1,
                    width: 42,
                    height: 42,
                    justifyContent: "center",
                  }}
                >
                  <Text
                    style={{
                      textAlign: "center",
                      color: "#818181",
                      fontSize: 16,
                    }}
                  >
                    {date.day}
                  </Text>
                </View>
              );
            }}
            markedDates={markedDays}
          />
        ) : (
            <AddQuarantineDates
              selected={selectedDays}
              onRemoveClicked={(index) => {
                selectedDays.splice(index, 1);
                let newSelection = [];
                selectedDays.forEach((s) => { newSelection.push(s) })
                setSelectedDays(newSelection);
              }}
              onDateRangeSelected={(sd, ed) => {
                selectedDays.push({
                  'start': sd,
                  'end': ed,
                  'key': makeid(4)
                })
                setSelectedDays(selectedDays);
              }}
            />
          )}
      </View>
    </View>
  );
}

const capitalizeWord = word => word.charAt(0).toUpperCase() + word.slice(1)

function ChooseStartAndEndDate({ type, date, month, onClick, key }) {
  return (
    <TouchableOpacity onPress={onClick} key={key}>
      <View style={{ marginTop: 12 }}>
        <Text style={styles.chooseYourDateTextStyle}>
          {capitalizeWord(type) + " date"}
        </Text>
        <Divider marginTop={8} />
        <View
          marginTop={12}
          paddingLeft={"5%"}
          style={{ flexDirection: "row" }}
        >
          <DateChip number={date} />
          <DateChip marginLeft={12} number={month} />
        </View>
      </View>
    </TouchableOpacity>
  );
}

function DateChip({ number, marginLeft }) {
  return (
    <View
      style={{
        height: 36,
        backgroundColor: "#E9E9E9",
        borderRadius: 8,
        width: 48,
        justifyContent: "center",
        marginLeft: marginLeft,
      }}
    >
      <Text style={{ fontSize: 16, fontWeight: "bold", textAlign: "center" }}>
        {number}
      </Text>
    </View>
  );
}

function AddQuarantineDates({
  selected,
  onRemoveClicked,
  onAddClicked,
  onDateRangeSelected,
}) {
  const [showDatePicker, setShowDatePicker] = React.useState(false);
  return (
    <View style={styles.addQuarantineDatesContainer}>
      <Modal visible={showDatePicker} transparent={true}>
        <View style={styles.modalContainerStyle}>
          <DateRangePicker
            onSuccess={(se, ed) => {
              setShowDatePicker(false);
              onDateRangeSelected(se, ed);
            }}
          />
        </View>
      </Modal>

      <Text
        style={{
          fontWeight: "bold",
          fontSize: 16,
          paddingTop: "3%",
          paddingLeft: "5%",
          paddingBottom: "3%",
        }}
      >
        {"When in doubt, Self-Quarantine"}
      </Text>
      <View style={{ height: "100%" }}>
        <ScrollView style={{ height: "80%" }}>
          {selected.map(({ start, end }, index) => {
            return <AddedQuarantineDateRange
              key={'add_date_range_' + index}
              start={start}
              end={end}
              onDeleteClick={() => {
                onRemoveClicked(index);
              }}
            />
          })}
          <AddMoreButton
            onClick={() => {
              setShowDatePicker(true);
            }}
          />
        </ScrollView>
        <View style={{ height: "10%" }}>
          <Divider />
          <View style={{ padding: "2%", justifyContent: "space-between" }}>
            <Text style={{ color: "blue", fontSize: 16 }}>{"< Back"}</Text>
          </View>
          <Divider />
        </View>
      </View>
    </View>
  );
}

function AddMoreButton({ onClick }) {
  return (
    <RectButton
      style={{
        width: 112,
        height: 36,
        backgroundColor: "#A9E7CB",
        borderColor: "#909090",
        borderWidth: 1,
        borderRadius: 8,
        marginTop: 32,
        marginLeft: "5%",
        justifyContent: "center",
      }}
      onPress={onClick}
    >
      <Text
        style={{ textAlign: "center", fontWeight: "bold", color: "#989898" }}
      >
        ADD
      </Text>
    </RectButton>
  );
}

function AddedQuarantineDateRange({ start, end, onDeleteClick }) {
  return (
    <View>
      <ChooseStartAndEndDate
        type="start"
        month={start.split("-")[1]}
        date={start.split("-")[2]}
      />
      <ChooseStartAndEndDate
        type="end"
        month={end.split("-")[1]}
        date={end.split("-")[2]}
      />
      <DeleteButton onClick={onDeleteClick} />
    </View>
  );
}

function DeleteButton({ key, onClick }) {
  return (
    <RectButton
      style={{
        width: 112,
        height: 36,
        backgroundColor: "#FFCEDF",
        borderColor: "#909090",
        borderWidth: 1,
        borderRadius: 8,
        marginTop: 12,
        marginLeft: "5%",
        justifyContent: "center",
      }}
      onPress={onClick}
    >
      <Text
        style={{ textAlign: "center", fontWeight: "bold", color: "#989898" }}
      >
        DELETE
      </Text>
    </RectButton>
  );
}

function ScreenHeader({ label, iconName, onClick }) {
  return (
    <TouchableOpacity onPress={onClick} styles={{ marginTop: 18 }}>
      <View style={styles.headerContainer}>
        <Text style={styles.text}>{label}</Text>
        <Ionicons
          name={iconName}
          size={28}
          color="gray"
          style={{ marginLeft: 8 }}
        />
      </View>
    </TouchableOpacity>
  );
}

function Divider({ marginTop }) {
  return (
    <View
      style={{
        backgroundColor: "gray",
        height: 1,
        marginTop: marginTop,
      }}
    />
  );
}

function OptionButton({ label, onPress, backgroundColor }) {
  return (
    <RectButton style={styles.buttonStyle} onPress={onPress}>
      <Text style={styles.buttonTextStyle}>{label}</Text>
    </RectButton>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#ffffff",
    height: "100%",
    flexDirection: "column",
    justifyContent: "flex-start",
  },
  headerContainer: {
    flexDirection: "row",
    justifyContent: "center",
  },
  contentContainer: {
    paddingTop: 15,
  },
  text: {
    fontSize: 18,
    height: "100%",
    alignContent: "center",
    color: "#1C1C1C",
    fontWeight: "bold",
    marginTop: 1,
  },

  buttonStyle: {
    backgroundColor: "#FFCEDF",
    borderColor: "#909090",
    borderWidth: 1,
    borderRadius: 8,
    height: 44,
    justifyContent: "center",
    width: 150,
  },

  buttonTextStyle: {
    fontSize: 18,
    color: "black",
    textAlign: "center",
    textAlignVertical: "center",
  },

  addQuarantineDatesContainer: {
    // padding: "5%",
  },

  chooseYourDateTextStyle: {
    fontSize: 16,
    paddingLeft: "5%",
  },

  modalContainerStyle: {
    margin: "10%",
    backgroundColor: "white",
    borderRadius: 20,
    padding: 35,
    alignItems: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 5,
  },
});
