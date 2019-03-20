package com.example.forev.mycodelibrary;

import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class SortAlgorithmAct extends BaseActivity {
//    https://www.cnblogs.com/onepixel/articles/7674659.html
    private final static String TAG = "SortAlgorithmAct";
    int[] mNumbers;
    @BindView(R.id.mNumbers)
    TextView mNumbersTextView;
    @BindView(R.id.mPopSortResult)
    TextView mPopSortResult;
    @BindView(R.id.mQuickSortResult)
    TextView mQuickSortResult;
    @BindView(R.id.mEasyInserSortResult)
    TextView mEasyInsertSortResult;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort_algorithm;
    }

    @Override
    protected void initView() {
        //生成若干位随机数
        int count = 10;
        int space = 100;
        mNumbers = new int[count];
        int n;
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            n = random.nextInt(space);
            mNumbers[i] = n;
        }
        printArray(mNumbers, mNumbersTextView);
    }

    @OnClick({R.id.mPopSort, R.id.mQuickSort, R.id.mEasyInsertSort})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.mPopSort:
                int[] result = popSort(mNumbers);
                printArray(result, mPopSortResult);
                break;
            case R.id.mQuickSort:
                int[] nu = Arrays.copyOf(mNumbers, mNumbers.length);
                quickSort(nu, 0, mNumbers.length - 1);
                printArray(nu, mQuickSortResult);
                break;
            case R.id.mEasyInsertSort:
                int[] nu1 = Arrays.copyOf(mNumbers, mNumbers.length);
                easyInsertSort(nu1);
                printArray(nu1, mEasyInsertSortResult);
                break;
        }
    }

    private void printArray(int[] numbers, TextView tv){
        if (null == numbers || null == tv){
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int k = 0; k < numbers.length; k++){
            builder.append(numbers[k] + ", ");
        }
        tv.setText(builder.toString());
    }

    private String printArray(int[] numbers){
        if (null == numbers){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int k = 0; k < numbers.length; k++){
            builder.append(numbers[k] + ", ");
        }
        return builder.toString();
    }

    private String printArray(int[] mNumbers, int start, int end){
        int[] r = Arrays.copyOfRange(mNumbers, start, end + 1);
        return printArray(r);
    }

    /**
     * 有小到大
     * 交换排序的一种
     * 两两递增比对，每次都把最大的推到后面去。排上几次就出来结果了。
     * 冒泡排序，复杂度为 n * n-1 * n-2 ...0 为 n!
     * @param originNumbers
     * @return
     */
    private int[] popSort(int[] originNumbers){
        //拷贝一份，不更改初始值
        int[] numbers = Arrays.copyOf(originNumbers, originNumbers.length);
        for (int k = numbers.length - 1; k >= 0; k--){
            for (int i = 0; i < k; i++){
                int t;
                if (numbers[i] > numbers[i+1]) {
                    //调换
                    t = numbers[i];
                    numbers[i] = numbers[i+1];
                    numbers[i+1] = t;
                }
            }
        }
        return numbers;
    }

    /**
     * 快排
     * 交换排序的一种，也是对冒泡排序的一种改进
     * 其核心原理是不断地分区，假设待比较的是b, 分成某个数的左边的数都小于b, 右边的数都大于b
     * 例如  3， 5， 1， 2， 6， 9
     * 以 3 为基准的话。 你就可以排成  1， 2， 3，5， 6， 9 不管你左右元素的顺序怎么样，反正就是严格遵守这个规则。
     * 小的在左边，大的在右边。
     * 然后对排下来的数再次进行分区，比较，直到比较到不能再划分为止。
     * 那么这种问题一看，八成和递归有关系。但是这都是以后的事情。咱们首先要做的是，如何让一个基准数的左边都小于它右边都大于它！！
     * 这个难倒了我
     * https://blog.csdn.net/adusts/article/details/80882649
     * 上面的链接中的文章很好的解释了一个事情
     * 就是基准值随便找一个。然后从左往右找到第一个大于基准值的数，再从右往左找到第一个小于基准值的数。这两个数相互转换，不久
     * 调成左边小右边大了么。
     *
     * @param originNumbers
     * @return
     */
    private void quickSort(int[] originNumbers, int start, int end){
        if (start >= end){
            return;
        }
        int[] numbers = Arrays.copyOf(originNumbers, originNumbers.length);
        int index = partSort(numbers, start, end);
        quickSort(numbers, start, index - 1);
        quickSort(numbers, index + 1, end);
    }

    /**
     *左面小，右面大
     * @param numbers
     * @param start
     * @param end
     * @return 返回分区界限点的坐标
     */
    private int partSort(int[] numbers, int start, int end) {
        int key = numbers[start];
        while (start < end) {
            while (start < end && key >= numbers[start]){
                start++;
            }
            while (start < end && key <= numbers[end]) {
                end -- ;
            }
            trans(numbers, start, end);
        }

        return start;
    }

    private void trans(int[] ints, int position1, int position2) {
        int t = ints[position1];
        ints[position1] = ints[position2];
        ints[position2] = t;
    }

    /**
     * 基本思想：

     　　把n个待排序的元素看成一个有序表和一个无序表，开始时有序表中只有一个元素，无序表中有n-1个元素；排序过程即每次从无序表中取出第一个元素，将它插入到有序表中，使之成为新的有序表，重复n-1次完成整个排序过程。

     　实例：

     　　0.初始状态 3，1，5，7，2，4，9，6（共8个数）

     　　   有序表：3；无序表：1，5，7，2，4，9，6

     　　1.第一次循环，从无序表中取出第一个数 1，把它插入到有序表中，使新的数列依旧有序

     　　   有序表：1，3；无序表：5，7，2，4，9，6

     　　2.第二次循环，从无序表中取出第一个数 5，把它插入到有序表中，使新的数列依旧有序

     　　   有序表：1，3，5；无序表：7，2，4，9，6

     　　3.第三次循环，从无序表中取出第一个数 7，把它插入到有序表中，使新的数列依旧有序

     　　   有序表：1，3，5，7；无序表：2，4，9，6

     　　4.第四次循环，从无序表中取出第一个数 2，把它插入到有序表中，使新的数列依旧有序

     　　   有序表：1，2，3，5，7；无序表：4，9，6

     　　5.第五次循环，从无序表中取出第一个数 4，把它插入到有序表中，使新的数列依旧有序

     　　   有序表：1，2，3，4，5，7；无序表：9，6

     　　6.第六次循环，从无序表中取出第一个数 9，把它插入到有序表中，使新的数列依旧有序

     　　   有序表：1，2，3，4，5，7，9；无序表：6

     　　7.第七次循环，从无序表中取出第一个数 6，把它插入到有序表中，使新的数列依旧有序

     　　   有序表：1，2，3，4，5，6，7，9；无序表：（空）
     * @param ints
     */
    private void easyInsertSort(int[] ints) {
        //就像扑克牌摸排排序差不多
        //首先拿出第i个参数，假设为1，那么让 1之后的所有元素和当前的这个数比较，一旦比当前的数还小，
        //两个数调换，总之就是让当前i位置上的数为目前最小！
        //然后，走了一遍之后，i的位置上肯定是最小的值了。
        //之后i++,也就是我该捋第二个最小的牌了。
        for (int i = 0; i < ints.length; i++){
            for(int k = i + 1; k < ints.length; k++) {
                if (ints[i] <= ints[k]) {
                    continue;
                } else {
                    trans(ints, i, k);
                }
            }
        }
    }

}
