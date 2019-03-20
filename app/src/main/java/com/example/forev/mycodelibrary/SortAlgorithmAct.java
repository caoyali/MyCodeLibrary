package com.example.forev.mycodelibrary;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;

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

    @OnClick({R.id.mPopSort, R.id.mQuickSort})
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
}
