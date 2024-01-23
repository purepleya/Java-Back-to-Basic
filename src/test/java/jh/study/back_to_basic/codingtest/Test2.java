package jh.study.back_to_basic.codingtest;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Test2 {

    @Test
    void test() {
        int[][] fees = {{4, 1000}, {6, 1000}, {21, 3000}, {16, 2000}, {26, 3000}};
        assertThat(solution(fees, 27)).isEqualTo(new int[]{3000, 4000});
    }

    @Test
    void test2() {
        int[][] fees2 = {{3, 40000}, {5, 60000}};
        assertThat(solution(fees2, 2)).isEqualTo(new int[]{30000, 40000});
    }

    @Test
    void test3() {
        int[][] fees3 = {{3, 40000}, {5, 60000}, {8, 90000}};
        assertThat(solution(fees3, 2)).isEqualTo(new int[]{30000, 30000});
    }


    private Map<Integer, FeeGroup> feeGroupMap = new HashMap<>();
    private List<Integer> feeList = new ArrayList<>();

    public int[] solution(int[][] fees, int t) {
        int[] answer = new int[]{0, 0};
        setData(fees);

        // 주차요금이 변경되는 구간 중 가장 시간이 짧은 구간을 구해서
        FeeGroup[] minDistanceFees = getMinDistanceFees();
        // 주차요금이 변경될 가능성이 있는 시간들을 구한다.
        int[] targetMins = getPossiblePointsOfChange(minDistanceFees);

        // 운임이 변경될 가능성이 있는 시간들의 약수목록을 이용해서 최대 주차요금과, 최소 주차요금을 구한다.
        int[][] minMaxPoints = getMinMaxPoints(targetMins);

        List<Integer> feeList = new ArrayList<>(
                Arrays.asList(
                    calculateFee(minMaxPoints[1][0], minMaxPoints[1][1], t),
                    calculateFee(minMaxPoints[0][0], minMaxPoints[0][1], t)
                )
        );
        feeList.sort(Comparator.naturalOrder());

        answer = feeList.stream().mapToInt(i -> i).toArray();

        return answer;
    }

    private int[][] getMinMaxPoints(int[] targetMins) {
        Set<Integer> possiblePoints = new HashSet<>();
        Set<Integer> imPossiblePoints = new HashSet<>();

        int[] minAb = new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] maxAb = new int[] {0, 0};

        for(int targetMin : targetMins) {
            int[] divisors = getDivisor(targetMin);

            for (int divisor: divisors) {
                if (possiblePoints.contains(divisor) || imPossiblePoints.contains(divisor)) {
                    continue;
                }

                if (this.feeList.size() > 1) {
                    if (divisor >= this.feeGroupMap.get(feeList.get(1)).getMinMin()) {
                        imPossiblePoints.add(divisor);
                        continue;
                    }
                }

                int a = divisor;
                int b = -1;
                boolean checkFlag = true;
                for(Map.Entry<Integer, FeeGroup> entry : feeGroupMap.entrySet()) {
                    FeeGroup feeGroup = entry.getValue();

                    int minMin = entry.getValue().getMinMin();
                    int newB = getB(a, minMin, feeGroup.fee);
                    if (newB < 0 || (b > 0 && b != newB)) {
                        checkFlag = false;
                        break;
                    } else {
                        b = newB;
                    }

                    if (feeGroup.getDuration() > 0) {
                        int maxMin = entry.getValue().getMaxMin();
                        newB = getB(a, maxMin, feeGroup.fee);
                        if (newB < 0 || b != newB) {
                            checkFlag = false;
                            break;
                        }
                    }
                }

                if (checkFlag) {
                    possiblePoints.add(divisor);

                    if (minAb[0] > a) {
                        minAb[0] = a;
                        minAb[1] = b;
                    }

                    if (maxAb[0] < a) {
                        maxAb[0] = a;
                        maxAb[1] = b;
                    }
                } else {
                    imPossiblePoints.add(divisor);
                }
            }
        }

        return new int[][] {minAb, maxAb};
    }

    private int getB(int a, int t, int fee) {
        int times = (int) Math.floor((double) t / a) + 1;
        if (fee % times == 0) {
            return fee / times;
        } else {
            return -1;
        }
    }

    private int calculateFee(int a, int b, int t) {
        return ((int) Math.floor((double) t / a) + 1) * b;
    }

    private int[] getDivisor(int value) {
        Set<Integer> divisors = new HashSet<>();
        for (int i = 1; i <= value; i++) {
            if (value % i == 0) {
                divisors.add(i);
                divisors.add(value / i);
            }

            if (i * i >= value) {
                break;
            }
        }

        return divisors.stream().sorted(Comparator.naturalOrder()).mapToInt(i -> i).toArray();
    }

    private int[] getPossiblePointsOfChange(FeeGroup[] feeGroups) {
        int minMin = feeGroups[0].maxMin;
        int maxMin = feeGroups[1].minMin;
        int[] points = new int[maxMin - minMin + 1];
        for (int i = minMin; i <= maxMin; i++) {
            points[i - minMin] = i;
        }

        return points;
    }


    private FeeGroup[] getMinDistanceFees() {
        int minDistance = Integer.MAX_VALUE;
        FeeGroup[] minDistanceFeeGroups = new FeeGroup[2];

        for (int i = 0; i < feeList.size() - 1; i++) {
            int smallFee = feeList.get(i);
            FeeGroup smallFeeGroup = feeGroupMap.get(smallFee);

            int biggerFee = feeList.get(i + 1);
            FeeGroup biggerFeeGroup = feeGroupMap.get(biggerFee);

            int distance = biggerFeeGroup.getMinMin() - smallFeeGroup.getMaxMin();
            if (minDistance > distance) {
                minDistance = distance;
                minDistanceFeeGroups[0] = smallFeeGroup;
                minDistanceFeeGroups[1] = biggerFeeGroup;
            }
        }

        return minDistanceFeeGroups;
    }

    private void setData(int[][] fees) {
        for (int[] fee : fees) {
            FeeGroup feeGroup = feeGroupMap.getOrDefault(fee[1], new FeeGroup(fee[1]));
            feeGroup.addMin(fee[0]);
            feeGroupMap.put(fee[1], feeGroup);
        }

        feeList = new ArrayList<>(feeGroupMap.keySet());
        feeList.sort(Integer::compareTo);
    }


    private class FeeGroup {
        int fee;
        int minMin = Integer.MAX_VALUE;
        int maxMin = -1;

        public int getMinMin() {
            return this.minMin;
        }

        public int getMaxMin() {
            return this.maxMin;
        }

        public int getDuration() {
            return maxMin - minMin;
        }

        public FeeGroup(int fee) {
            this.fee = fee;
        }

        public void addMin(int min) {
            if (minMin > min) {
                minMin = min;
            }
            if (maxMin < min) {
                maxMin = min;
            }
        }

        public String toString() {
            return String.format("{fee: %d, minMin: %d, maxMin: %d, duration: %d}", fee, minMin, maxMin, getDuration());
        }
    }
}
