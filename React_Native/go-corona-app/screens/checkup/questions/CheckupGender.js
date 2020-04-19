import * as React from 'react';
import { StyleSheet, Text, View, ScrollView } from 'react-native';
import QuestionIcon from '../../../assets/images/Question.svg';
import LinkIcon from '../../../assets/images/Link.svg';
import ProgressBarContainer from '../../../components/ProgressBar';
import CustomButton from '../../../components/button/index';

export default function CheckupGender() {
  const text = "Patient"
  const pageNo = 4;

  return (
    <ScrollView>
    <View style={styles.viewContainer}>
        <View style={styles.container}>
            <View style={styles.sectionContainer}>
                <Text style={styles.title}>Please select your gender</Text>
                <View style={styles.helpLinks}>
                    <CustomButton Icon={QuestionIcon} label="Male" />
                    <CustomButton Icon={LinkIcon} label="Female" />
                    <CustomButton Icon={LinkIcon} label="Other" />
                </View>
            </View>
        </View>
    </View>
</ScrollView>
  );
}

const styles = StyleSheet.create({
  viewContainer: {
      flex: 1,
      backgroundColor: '#fafafa',
      justifyContent: 'flex-start'
  },
  container: {
      justifyContent: 'center'
  },
  sectionContainer: {
      marginTop: 40,
      marginBottom: 40,
      alignItems: 'center',
  },
  title: {
      fontSize: 18,
      fontWeight: "bold",
  },
  helpLinks: {
      marginTop: 30,
      padding: 5,
      width: "90%"
  }
});
