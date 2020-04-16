import * as React from 'react';
import { StyleSheet, Text, View, Image, ScrollView } from 'react-native';

import StepList from '../../components/stepper/StepList';
import Step from '../../components/stepper/Step';
import Introduction from './Introduction';
import Terms from './Terms';
import Question1 from './questions/1-Question'
import Question2 from './questions/2-Question'
import HeightQuestion from './questions/Height';
import AgeQuestion from './questions/Age';
import WeightQuestion from './questions/Weight';

export default function CheckupScreen() {
  return (
    <View style={styles.container}>
      <StepList>
        <Step component={Introduction} />
        <Step component={Terms} />
        <Step component={Question1} />
        <Step component={Question2} />
        <Step component={AgeQuestion} />
        <Step component={HeightQuestion} />
        <Step component={WeightQuestion} />
      </StepList>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fafafa',
    justifyContent: 'flex-start'
  },
});
