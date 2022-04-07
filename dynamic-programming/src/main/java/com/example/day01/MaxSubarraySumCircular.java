package com.example.day01;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * @author tang
 * @date 2022/4/7 12:00
 *
 *  给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。
 *  环形数组 意味着数组的末端将会与开头相连呈环状。形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ， nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。
 *  子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。形式上，对于子数组 nums[i], nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。
 *
 *  实例 输入：nums = [1,-2,3,-2]
 *      输出：3
 *      解释：从子数组 [3] 得到最大和 3
 */
@Slf4j
public class MaxSubarraySumCircular {
    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            int[] nums = stringToIntegerArray(line);

            int ret = new Solution().maxSubarraySumCircular(nums);

            String out = String.valueOf(ret);

            log.info("nums = {}, result = {}",nums, out);
        }
    }
}


class Solution {
    public int maxSubarraySumCircular(int[] nums) {
        int n = nums.length;
        int[] sum = new int[n * 2 + 1];
        for (int i = 0;i < 2 * n;i++) {
            sum[i + 1] = sum[i] + nums[i % n];
        }

        int ans = nums[0];

        // 维护一个双向队列
        LinkedList<Integer> deque = new LinkedList<>();
        deque.offer(0);
        for (int i = 1;i <= 2 * n;i++) {
            // 当队列第一个不在 n 的范围内是 去除
            if (deque.peekFirst() < i - n) {
                deque.pollFirst();
            }

            // 获取当前数值
            ans = Math.max(ans, sum[i] - sum[deque.peekFirst()]);

            // 维持一个递增队列
            while(!deque.isEmpty() && sum[i] < sum[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        return ans;
    }
}